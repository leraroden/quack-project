export class News {
  publishedOn: Date;
  headline: string;
  content: string;

  static fromObject(object: any): News {
    const n = new News();
    n.headline = object.headline;
    n.content = object.content;
    n.publishedOn = new Date(object.publishedOn);
    return n;
  }

  get isPublishedToday(): boolean {
    const now = new Date();
    return now.getDate() === this.publishedOn.getDate()
      && now.getMonth() === this.publishedOn.getMonth()
      && now.getFullYear() === this.publishedOn.getFullYear();
  }
}
