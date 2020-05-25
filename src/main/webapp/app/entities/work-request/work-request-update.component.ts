import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IWorkRequest, WorkRequest } from 'app/shared/model/work-request.model';
import { WorkRequestService } from './work-request.service';
import { IWork } from 'app/shared/model/work.model';
import { WorkService } from 'app/entities/work/work.service';
import { IAppUser } from 'app/shared/model/app-user.model';
import { AppUserService } from 'app/entities/app-user/app-user.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';

type SelectableEntity = IWork | IAppUser | IAddress;

@Component({
  selector: 'jhi-work-request-update',
  templateUrl: './work-request-update.component.html',
})
export class WorkRequestUpdateComponent implements OnInit {
  isSaving = false;
  works: IWork[] = [];
  appusers: IAppUser[] = [];
  addresses: IAddress[] = [];

  editForm = this.fb.group({
    id: [],
    creationDateTime: [],
    forMysef: [],
    constructionSite: [],
    mediatorPercentage: [],
    detailedDescription: [],
    magnitude: [],
    estimatedWorkFees: [],
    plannedStartDateTime: [],
    plannedEndDateTime: [],
    status: [],
    work: [],
    user: [],
    address: [],
  });

  constructor(
    protected workRequestService: WorkRequestService,
    protected workService: WorkService,
    protected appUserService: AppUserService,
    protected addressService: AddressService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workRequest }) => {
      if (!workRequest.id) {
        const today = moment().startOf('day');
        workRequest.creationDateTime = today;
        workRequest.plannedStartDateTime = today;
        workRequest.plannedEndDateTime = today;
      }

      this.updateForm(workRequest);

      this.workService.query().subscribe((res: HttpResponse<IWork[]>) => (this.works = res.body || []));

      this.appUserService.query().subscribe((res: HttpResponse<IAppUser[]>) => (this.appusers = res.body || []));

      this.addressService.query().subscribe((res: HttpResponse<IAddress[]>) => (this.addresses = res.body || []));
    });
  }

  updateForm(workRequest: IWorkRequest): void {
    this.editForm.patchValue({
      id: workRequest.id,
      creationDateTime: workRequest.creationDateTime ? workRequest.creationDateTime.format(DATE_TIME_FORMAT) : null,
      forMysef: workRequest.forMysef,
      constructionSite: workRequest.constructionSite,
      mediatorPercentage: workRequest.mediatorPercentage,
      detailedDescription: workRequest.detailedDescription,
      magnitude: workRequest.magnitude,
      estimatedWorkFees: workRequest.estimatedWorkFees,
      plannedStartDateTime: workRequest.plannedStartDateTime ? workRequest.plannedStartDateTime.format(DATE_TIME_FORMAT) : null,
      plannedEndDateTime: workRequest.plannedEndDateTime ? workRequest.plannedEndDateTime.format(DATE_TIME_FORMAT) : null,
      status: workRequest.status,
      work: workRequest.work,
      user: workRequest.user,
      address: workRequest.address,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workRequest = this.createFromForm();
    if (workRequest.id !== undefined) {
      this.subscribeToSaveResponse(this.workRequestService.update(workRequest));
    } else {
      this.subscribeToSaveResponse(this.workRequestService.create(workRequest));
    }
  }

  private createFromForm(): IWorkRequest {
    return {
      ...new WorkRequest(),
      id: this.editForm.get(['id'])!.value,
      creationDateTime: this.editForm.get(['creationDateTime'])!.value
        ? moment(this.editForm.get(['creationDateTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      forMysef: this.editForm.get(['forMysef'])!.value,
      constructionSite: this.editForm.get(['constructionSite'])!.value,
      mediatorPercentage: this.editForm.get(['mediatorPercentage'])!.value,
      detailedDescription: this.editForm.get(['detailedDescription'])!.value,
      magnitude: this.editForm.get(['magnitude'])!.value,
      estimatedWorkFees: this.editForm.get(['estimatedWorkFees'])!.value,
      plannedStartDateTime: this.editForm.get(['plannedStartDateTime'])!.value
        ? moment(this.editForm.get(['plannedStartDateTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      plannedEndDateTime: this.editForm.get(['plannedEndDateTime'])!.value
        ? moment(this.editForm.get(['plannedEndDateTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      status: this.editForm.get(['status'])!.value,
      work: this.editForm.get(['work'])!.value,
      user: this.editForm.get(['user'])!.value,
      address: this.editForm.get(['address'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkRequest>>): void {
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
