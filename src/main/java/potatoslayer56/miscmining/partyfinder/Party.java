package potatoslayer56.miscmining.partyfinder;

import java.util.List;

public class Party {
  public int id;
  public String note;
  public String owner;
  public int size;

  public Party(int id, String n, String o, int s) {
    this.id = id;
    this.note = n;
    this.owner = o;
    this.size = s;
  }
}
