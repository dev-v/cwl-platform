/**
 * pub包下为公共的任何请求都可以访问都接口，路径以/pub/**开头
 * auth包下为需要进行身份认证才能访问都接口，路径以/api/**开头
 * service包下为内部系统可任意调用都接口，路径以/service/**开头
 */
package com.dber.#{packageName}.api;

