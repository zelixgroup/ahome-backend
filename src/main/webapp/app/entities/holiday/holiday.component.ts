import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHoliday } from 'app/shared/model/holiday.model';
import { HolidayService } from './holiday.service';
import { HolidayDeleteDialogComponent } from './holiday-delete-dialog.component';

@Component({
  selector: 'jhi-holiday',
  templateUrl: './holiday.component.html',
})
export class HolidayComponent implements OnInit, OnDestroy {
  holidays?: IHoliday[];
  eventSubscriber?: Subscription;

  constructor(protected holidayService: HolidayService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.holidayService.query().subscribe((res: HttpResponse<IHoliday[]>) => (this.holidays = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInHolidays();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IHoliday): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInHolidays(): void {
    this.eventSubscriber = this.eventManager.subscribe('holidayListModification', () => this.loadAll());
  }

  delete(holiday: IHoliday): void {
    const modalRef = this.modalService.open(HolidayDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.holiday = holiday;
  }
}
