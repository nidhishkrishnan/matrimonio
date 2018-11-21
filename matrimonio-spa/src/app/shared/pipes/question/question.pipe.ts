import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'question'
})
export class QuestionPipe implements PipeTransform {
  transform(value: boolean): string {
    return value == true ? 'Yes' : 'No';
  };
}
