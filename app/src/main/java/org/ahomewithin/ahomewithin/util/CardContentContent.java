package org.ahomewithin.ahomewithin.util;

/**
 * Created by xiangyang_xiao on 3/13/16.
 */
public class CardContentContent{
  public static final int MIND = 0;
  public static final int BODY = 1;
  public static final int HEART = 2;
  public static final int SOUL = 3;

  public static CardContentContent getDefault() {
    return new CardContentContent("quote", "quote_authoer", "reflection", "action");
  }

  public String getQuote() {
    return quote;
  }

  public void setQuote(String quote) {
    this.quote = quote;
  }

  public String getQuote_author() {
    return quote_author;
  }

  public void setQuote_author(String quote_author) {
    this.quote_author = quote_author;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getReflection() {
    return reflection;
  }

  public void setReflection(String reflection) {
    this.reflection = reflection;
  }

  public int type;
  private String quote;
  private String quote_author;
  private String reflection;
  private String action;

  public CardContentContent(String quote, String quote_author, String reflection, String action) {
    this.quote = quote;
    this.quote_author = quote_author;
    this.reflection = reflection;
    this.action = action;
  }

  public CardContentContent(int type, String quote, String quote_author, String reflection, String action) {
    this.type = type;
    this.quote = quote;
    this.quote_author = quote_author;
    this.reflection = reflection;
    this.action = action;
  }
}
