import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAddress, Address } from 'app/shared/model/address.model';
import { AddressService } from './address.service';
import { ICity } from 'app/shared/model/city.model';
import { CityService } from 'app/entities/city/city.service';
import { IAppUser } from 'app/shared/model/app-user.model';
import { AppUserService } from 'app/entities/app-user/app-user.service';

type SelectableEntity = ICity | IAppUser;

@Component({
  selector: 'jhi-address-update',
  templateUrl: './address-update.component.html',
})
export class AddressUpdateComponent implements OnInit {
  isSaving = false;
  cities: ICity[] = [];
  appusers: IAppUser[] = [];

  editForm = this.fb.group({
    id: [],
    type: [],
    location: [],
    geolocation: [],
    primaryPhoneNumber: [],
    secondaryPhoneNumber: [],
    emailAddress: [],
    city: [],
    appUser: [],
  });

  constructor(
    protected addressService: AddressService,
    protected cityService: CityService,
    protected appUserService: AppUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ address }) => {
      this.updateForm(address);

      this.cityService.query().subscribe((res: HttpResponse<ICity[]>) => (this.cities = res.body || []));

      this.appUserService.query().subscribe((res: HttpResponse<IAppUser[]>) => (this.appusers = res.body || []));
    });
  }

  updateForm(address: IAddress): void {
    this.editForm.patchValue({
      id: address.id,
      type: address.type,
      location: address.location,
      geolocation: address.geolocation,
      primaryPhoneNumber: address.primaryPhoneNumber,
      secondaryPhoneNumber: address.secondaryPhoneNumber,
      emailAddress: address.emailAddress,
      city: address.city,
      appUser: address.appUser,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const address = this.createFromForm();
    if (address.id !== undefined) {
      this.subscribeToSaveResponse(this.addressService.update(address));
    } else {
      this.subscribeToSaveResponse(this.addressService.create(address));
    }
  }

  private createFromForm(): IAddress {
    return {
      ...new Address(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      location: this.editForm.get(['location'])!.value,
      geolocation: this.editForm.get(['geolocation'])!.value,
      primaryPhoneNumber: this.editForm.get(['primaryPhoneNumber'])!.value,
      secondaryPhoneNumber: this.editForm.get(['secondaryPhoneNumber'])!.value,
      emailAddress: this.editForm.get(['emailAddress'])!.value,
      city: this.editForm.get(['city'])!.value,
      appUser: this.editForm.get(['appUser'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddress>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
