export class Quack {
  content: string;
  publishedOn: Date;
  authorName: String;

  static fromObject(object: any): Quack {
    const q = new Quack();
    q.content = object.content;
    q.publishedOn = new Date(object.publishedOn);
    q.authorName = object.authorName;
    return q;
  }
}
