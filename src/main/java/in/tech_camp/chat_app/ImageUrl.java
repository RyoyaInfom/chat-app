package in.tech_camp.chat_app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;


@Component
@NoArgsConstructor
public class ImageUrl {
  @Value("${image.url}") //application.propertiesのimage.urlの値をマッピング
  private String url;

  public String getImageUrl(){ //画像ファイルを格納する場所を取得するメソッド
    return url;
  }
}
