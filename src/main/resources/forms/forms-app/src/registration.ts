import {Participant} from './participant';

export interface Registration {
  firstName: string,
  lastName: string,
  email: string,
  participants: Participant[]
}
