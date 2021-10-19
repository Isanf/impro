export interface IUserDeviceId {
  id?: number;
  adressMac?: string;
  deviceId?: string;
  userLogin?: string;
  userId?: number;
}

export class UserDeviceId implements IUserDeviceId {
  constructor(public id?: number, public adressMac?: string, public deviceId?: string, public userLogin?: string, public userId?: number) {}
}
