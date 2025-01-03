export interface Account {
  id: number,
  externalId: string | undefined,
  name: string,
  iconUrl: string,

  registrationDate: string,
  lastLoginDate: string | undefined,

  email: string | undefined,
  phone: string | undefined,
  mobile: string | undefined,

  firstName: string,
  lastName: string,

  serviceAccount: boolean,
  idpLinked: boolean,
}

export class AccountChangeRequest {
  constructor(
    public name: string,
    public iconUrl: string,
    public externalId: string | undefined,
    public email: string | undefined,
    public phone: string | undefined,
    public mobile: string | undefined,
    public firstName: string,
    public lastName: string,
  ) {
  }
}

export interface AccountInfo {
  id: number,
  name: string,
  iconUrl: string,
  email: string,
  firstName: string,
  lastName: string,
}

export interface AccountValidationResult {
  created: boolean,
  account: Account,
  info: AccountInfo
}


export class AccountSetupRequest {
  constructor(
    public account: AccountChangeRequest
  ) {
  }
}


export interface AccountDetails {
  id: number,
  name: string,
  iconUrl: string,
  email: string,
  phone: string,
  mobile: string,
  firstName: string,
  lastName: string,
}
