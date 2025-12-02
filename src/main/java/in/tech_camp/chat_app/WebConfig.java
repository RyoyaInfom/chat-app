package in.tech_camp.chat_app;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  //引数：静的ファイルの扱いをカスタマイズするためのオブジェクト
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // 「/uploads/**というURLパターンで表示するリクエストが発生した場合
    registry.addResourceHandler("/uploads/**")
    //src/main/resources/static/uploads/ ディレクトリの内容を見る」
    //(file:をつけることで、指定するパスをサーバー内で保持しているものではなく、ファイルシステム上の特定ディレクトリから直接読み込むようになる)
            .addResourceLocations("file:src/main/resources/static/uploads/");
  }
}