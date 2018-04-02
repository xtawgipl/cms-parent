package cn.edu.uestc.cms.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 项目常量配置
 *
 * @author zhangjj
 * @create 2017-08-22 15:07
 **/
@Component
@ConfigurationProperties(prefix = "constant")
public class Constant {

    private String photoUrlPrefix;

    private String photoPathPrefix;

    private Photo photo;

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public String getPhotoUrlPrefix() {
        return photoUrlPrefix;
    }

    public void setPhotoUrlPrefix(String photoUrlPrefix) {
        this.photoUrlPrefix = photoUrlPrefix;
    }

    public String getPhotoPathPrefix() {
        return photoPathPrefix;
    }

    public void setPhotoPathPrefix(String photoPathPrefix) {
        this.photoPathPrefix = photoPathPrefix;
    }
}
