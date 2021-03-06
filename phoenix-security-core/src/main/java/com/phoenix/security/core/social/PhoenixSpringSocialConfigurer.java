package com.phoenix.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * User: sheng
 * Date: 2018-03-31 18:37
 * Description:SpringSocialConfigurer配置类，因为SpringSocialConfigurer中的filterProcessesUrl是写死的，
 *  不能更改，所以继承自filterProcessesUrl，重写它的postProcess(T object)方法
 */
public class PhoenixSpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    private SocialAuthenticationFilterPostProcess socialAuthenticationFilterPostProcess;

    public PhoenixSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    protected <T> T postProcess(T object) {

        SocialAuthenticationFilter filter = (SocialAuthenticationFilter)super.postProcess(object);
        filter.setFilterProcessesUrl(this.filterProcessesUrl);

        //针对App实现设置AuthenticationSuccessHandler
        if(this.socialAuthenticationFilterPostProcess != null) {
            this.socialAuthenticationFilterPostProcess.process(filter);
        }

        return (T) filter;
    }

    public void setSocialAuthenticationFilterPostProcess(SocialAuthenticationFilterPostProcess socialAuthenticationFilterPostProcess) {
        this.socialAuthenticationFilterPostProcess = socialAuthenticationFilterPostProcess;
    }

    public SocialAuthenticationFilterPostProcess getSocialAuthenticationFilterPostProcess() {
        return socialAuthenticationFilterPostProcess;
    }
}
