import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Diagnosticoscie10Service } from 'app/entities/diagnosticoscie10/diagnosticoscie10.service';
import { IDiagnosticoscie10, Diagnosticoscie10 } from 'app/shared/model/diagnosticoscie10.model';

describe('Service Tests', () => {
    describe('Diagnosticoscie10 Service', () => {
        let injector: TestBed;
        let service: Diagnosticoscie10Service;
        let httpMock: HttpTestingController;
        let elemDefault: IDiagnosticoscie10;
        let expectedResult: IDiagnosticoscie10 | IDiagnosticoscie10[] | boolean | null;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            expectedResult = null;
            injector = getTestBed();
            service = injector.get(Diagnosticoscie10Service);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Diagnosticoscie10(0, 'AAAAAAA', 'AAAAAAA', 0);
        });

        describe('Service methods', () => {
            it('should find an element', () => {
                const returnedFromService = Object.assign({}, elemDefault);

                service.find(123).subscribe(resp => (expectedResult = resp.body));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(returnedFromService);
                expect(expectedResult).toMatchObject(elemDefault);
            });

            it('should create a Diagnosticoscie10', () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);

                service.create(new Diagnosticoscie10()).subscribe(resp => (expectedResult = resp.body));

                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(returnedFromService);
                expect(expectedResult).toMatchObject(expected);
            });

            it('should update a Diagnosticoscie10', () => {
                const returnedFromService = Object.assign(
                    {
                        clave: 'BBBBBB',
                        descripcion: 'BBBBBB',
                        idcategoria: 1
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);

                service.update(expected).subscribe(resp => (expectedResult = resp.body));

                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(returnedFromService);
                expect(expectedResult).toMatchObject(expected);
            });

            it('should return a list of Diagnosticoscie10', () => {
                const returnedFromService = Object.assign(
                    {
                        clave: 'BBBBBB',
                        descripcion: 'BBBBBB',
                        idcategoria: 1
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

            it('should delete a Diagnosticoscie10', () => {
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
