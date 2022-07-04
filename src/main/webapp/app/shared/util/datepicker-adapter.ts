/**
 * Angular bootstrap Date adapter
 */
import { Injectable } from '@angular/core';
import { NgbDateAdapter, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { Dayjs } from 'dayjs';
import * as dayjs from 'dayjs';

@Injectable()
export class NgbDateMomentAdapter extends NgbDateAdapter<Dayjs> {
    fromModel(date: Dayjs): NgbDateStruct {
        if (date != null && date.isValid()) {
            return { year: date.year(), month: date.month() + 1, day: date.date() };
        }
        return null;
    }

    toModel(date: NgbDateStruct): Dayjs {
        return date ? dayjs(date.year + '-' + date.month + '-' + date.day, 'YYYY-MM-DD') : null;
    }
}
