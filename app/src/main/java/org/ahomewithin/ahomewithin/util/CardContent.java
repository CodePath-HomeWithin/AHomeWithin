package org.ahomewithin.ahomewithin.util;

/**
 * Created by xiangyang_xiao on 3/13/16.
 */
public class CardContent {

  public static CardContent getDefault() {
    return new CardContent(
        CardContentContent.getDefault(),
        CardContentContent.getDefault(),
        CardContentContent.getDefault(),
        CardContentContent.getDefault()
    );
  }
  public CardContentContent getMind() {
    return mind;
  }

  public void setMind(CardContentContent mind) {
    this.mind = mind;
  }

  public CardContentContent getBody() {
    return body;
  }

  public void setBody(CardContentContent body) {
    this.body = body;
  }

  public CardContentContent getHeart() {
    return heart;
  }

  public void setHeart(CardContentContent heart) {
    this.heart = heart;
  }

  public CardContentContent getSoul() {
    return soul;
  }

  public void setSoul(CardContentContent soul) {
    this.soul = soul;
  }

  public CardContent(CardContentContent body, CardContentContent heart, CardContentContent soul, CardContentContent mind) {
    this.body = body;
    this.heart = heart;
    this.soul = soul;
    this.mind = mind;
  }

  private CardContentContent mind;
  private CardContentContent body;
  private CardContentContent heart;
  private CardContentContent soul;
}
