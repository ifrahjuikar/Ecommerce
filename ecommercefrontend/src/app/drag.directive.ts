import { Directive, EventEmitter, HostBinding, HostListener, Output } from '@angular/core';
import { FileHandle } from './_model/file-handle-model';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

@Directive({
  selector: '[appDrag]'
})
export class DragDirective {
  @Output() files: EventEmitter<FileHandle> = new EventEmitter<FileHandle>();

  @HostBinding('style.background') private background = '#eee';

  constructor(private sanitizer: DomSanitizer) {}

  @HostListener('dragover', ['$event'])
  public onDragOver(event: DragEvent): void {
    if (event) {
      event.preventDefault(); // Ensure event is not undefined
      event.stopPropagation();
      this.background = '#999';
    }
  }

  @HostListener('dragleave', ['$event'])
  public onDragLeave(event: DragEvent): void {
    if (event) {
      event.preventDefault();
      event.stopPropagation();
      this.background = '#eee';
    }
  }

  @HostListener('drop', ['$event'])
  public onDrop(event: DragEvent): void {
    if (event) {
      event.preventDefault();
      event.stopPropagation();
      this.background = '#eee';

      if (event.dataTransfer) {
        const file = event.dataTransfer.files[0];
        const url: SafeUrl = this.sanitizer.bypassSecurityTrustUrl(
          window.URL.createObjectURL(file)
        );

        const fileHandle: FileHandle = { file, url };
        this.files.emit(fileHandle);
      } else {
        console.error('No dataTransfer object available.');
      }
    }
  }
}
