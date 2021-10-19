import { trigger, style, animate, transition, state } from '@angular/animations';

export const fade = [
  trigger('fade', [state('in', style({ opacity: '1' })), state('out', style({ opacity: '0' })), transition('* <=> *', [animate(1000)])])
];
