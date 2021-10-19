export interface IUserOtp {
  id?: number;
  otpNumber?: number;
  otpUsed?: boolean;
  userLogin?: string;
  userId?: number;
}

export class UserOtp implements IUserOtp {
  constructor(public id?: number, public otpNumber?: number, public otpUsed?: boolean, public userLogin?: string, public userId?: number) {
    this.otpUsed = this.otpUsed || false;
  }
}
