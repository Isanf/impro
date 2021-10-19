import { Directive, OnInit, ElementRef, Renderer2, Input } from '@angular/core';
import { TranslateService, LangChangeEvent } from '@ngx-translate/core';

@Directive({
  selector: '[jhiActiveMenu]'
})
export class ActiveMenuDirective implements OnInit {
  @Input() jhiActiveMenu: string;

  constructor(private el: ElementRef, private Renderer: Renderer2, private translateService: TranslateService) {}

  ngOnInit() {
    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.updateActiveFlag(event.lang);
    });
    this.updateActiveFlag(this.translateService.currentLang);
  }

  updateActiveFlag(selectedLanguage) {
    if (this.jhiActiveMenu === selectedLanguage) {
      this.Renderer.setProperty(this.el.nativeElement, 'active', true);
    } else {
      this.Renderer.setProperty(this.el.nativeElement, 'active', false);
    }
  }
}
