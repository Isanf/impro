import { Moment } from 'moment';
import { TypeDocIdentification } from 'app/shared/model/enumerations/type-doc-identification.model';

export interface IDocIdentificationPP {
  id?: number;
  numeroDoc?: string;
  nip?: string;
  dateEtablissement?: Moment;
  lieuEtablissement?: string;
  autoriteEmettrice?: string;
  typeDocIdentification?: TypeDocIdentification;
}

export class DocIdentificationPP implements IDocIdentificationPP {
  constructor(
    public id?: number,
    public numeroDoc?: string,
    public nip?: string,
    public dateEtablissement?: Moment,
    public lieuEtablissement?: string,
    public autoriteEmettrice?: string,
    public typeDocIdentification?: TypeDocIdentification
  ) {}
}
