import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Categoriascie10Service } from 'app/entities/categoriascie10/categoriascie10.service';
import { ICategoriascie10, Categoriascie10 } from 'app/shared/model/categoriascie10.model';

describe('Service Tests', () => {
    describe('Categoriascie10 Service', () => {
        let injector: TestBed;
        let service: Categoriascie10Service;
        let httpMock: HttpTestingController;
        let elemDefault: ICategoriascie10;
        let expectedResult: ICategoriascie10 | ICategoriascie10[] | boolean | null;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            expectedResult = null;
            injector = getTestBed();
            service = injector.get(Categoriascie10Service);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Categoriascie10(0, 'AAAAAAA', 'AAAAAAA', 0);
        });

        describe('Service methods', () => {
            it('should find an element', () => {
                const returnedFromService = Object.assign({}, elemDefault);

                service.find(123).subscribe(resp => (expectedResult = resp.body));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(returnedFromService);
                expect(expectedResult).toMatchObject(elemDefault);
            });

            it('should create a Categoriascie10', () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);

                service.create(new Categoriascie10()).subscribe(resp => (expectedResult = resp.body));

                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(returnedFromService);
                expect(expectedResult).toMatchObject(expected);
            });

            it('should update a Categoriascie10', () => {
                const returnedFromService = Object.assign(
                    {
                        clave: 'BBBBBB',
                        descripcion: 'BBBBBB',
                        idsubgrupo: 1
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);

                service.update(expected).subscribe(resp => (expectedResult = resp.body));

                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(returnedFromService);
                expect(expectedResult).toMatchObject(expected);
            });

            it('should return a list of Categoriascie10', () => {
                const returnedFromService = Object.assign(
                    {
                        clave: 'BBBBBB',
                        descripcion: 'BBBBBB',
                        idsubgrupo: 1
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

            it('should delete a Categoriascie10', () => {
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
