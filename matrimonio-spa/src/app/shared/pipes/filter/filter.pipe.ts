import { Pipe, PipeTransform } from '@angular/core';
@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {
  transform(items: any[], searchText: Boolean): any[] {
    if (!items) return [];
    const filtered = items.filter(it => {
      return it.hidden == searchText;
    });
    return filtered;
  }
}
