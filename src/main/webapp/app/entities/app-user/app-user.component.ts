import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAppUser } from 'app/shared/model/app-user.model';
import { AppUserService } from './app-user.service';
import { AppUserDeleteDialogComponent } from './app-user-delete-dialog.component';

@Component({
  selector: 'jhi-app-user',
  templateUrl: './app-user.component.html',
})
export class AppUserComponent implements OnInit, OnDestroy {
  appUsers?: IAppUser[];
  eventSubscriber?: Subscription;

  constructor(protected appUserService: AppUserService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.appUserService.query().subscribe((res: HttpResponse<IAppUser[]>) => (this.appUsers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAppUsers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAppUser): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAppUsers(): void {
    this.eventSubscriber = this.eventManager.subscribe('appUserListModification', () => this.loadAll());
  }

  delete(appUser: IAppUser): void {
    const modalRef = this.modalService.open(AppUserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.appUser = appUser;
  }
}
