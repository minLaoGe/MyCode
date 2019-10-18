package cn.itcast.travel.domain;

public class DaoInfo implements Cloneable {
  private boolean flag;
  private String message;

  public boolean isFlag() {
    return flag;
  }

  public void setFlag(boolean flag) {
    this.flag = flag;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public DaoInfo() {
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }


  public DaoInfo(boolean flag, String message) {
    this.flag = flag;
    this.message = message;
  }
}
