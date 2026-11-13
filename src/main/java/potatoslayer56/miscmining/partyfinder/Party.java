package potatoslayer56.miscmining.partyfinder;

import java.util.List;

public class Party {
  public int id;
  public String note;
  public List<List<String>> members;

  public Party(int id, String n, List<List<String>> m) {
    this.id = id;
    this.note = n;
    this.members = m;
  }

  public String getPartyDisplayNames() {
    StringBuilder str = new StringBuilder();
    members.forEach(member -> {
      str.append(member.getLast());
      if (members.getLast() != member) {str.append(", ");}
    });
    return str.toString();
  }
}
