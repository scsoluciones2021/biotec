import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Subgruposcie10Service } from 'app/entities/subgruposcie10/subgruposcie10.service';
import { ISubgruposcie10, Subgruposcie10 } from 'app/shared/model/subgruposcie10.model';

describe('Service Tests', () => {
    describe('Subgruposcie10 Service', () => {
        let injector: TestBed;
        let service: Subgruposcie10Service;
        let httpMock: HttpTestingController;
        let elemDefault: ISubgruposcie10;
        let expectedResult: ISubgruposcie10 | ISubgruposcie10[] | boolean | null;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            expectedResult = null;
            injector = getTestBed();
            service = injector.get(Subgruposcie10Service);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Subgruposcie10(0, 'AAAAAAA', 'AAAAAAA', 0);
        });

        describe('Service methods', () => {
            it('should find an element', () => {
                const returnedFromService = Object.assign({}, elemDefault);

                service.find(123).subscribe(resp => (expectedResult = resp.body));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(returnedFromService);
                expect(expectedResult).toMatchObject(elemDefault);
            });

            it('should create a Subgruposcie10', () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);

                service.create(new Subgruposcie10()).subscribe(resp => (expectedResult = resp.body));

                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(returnedFromService);
                expect(expectedResult).toMatchObject(expected);
            });

            it('should update a Subgruposcie10', () => {
                const returnedFromService = Object.assign(
                    {
                        clave: 'BBBBBB',
                        descripcion: 'BBBBBB',
                        idGrupo: 1
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);

                service.update(expected).subscribe(resp => (expectedResult = resp.body));

                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(returnedFromService);
                expect(expectedResult).toMatchObject(expected);
            });

            it('should return a list of Subgruposcie10', () => {
                const returnedFromService = Object.assign(
                    {
                        clave: 'BBBBBB',
                        descripcion: 'BBBBBB',
                        idGrupo: 1
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);

                service.query().subscribe(resp => (expectedResult = resp.body));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush([returnedFromService]);
                httpMock.verify();
                expect(expectedResult).toContainEqual(expected);
            });

            it('should delete a Subgruposcie10', () => {
                service.delete(123).subscribe(resp => (expectedResult = resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
                expect(expectedResult);
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
