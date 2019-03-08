package com.zz.core.exception;

/**
 * Created with IntelliJ IDEA.
 * User: fengchen
 * Date: 14-4-17
 * Time: 下午3:51
 * To change this template use File | Settings | File Templates.
 * 异常信息记录管理
 */
public enum ExceptionCodes {
    RECODE_IS_EXIST("00001","{0}记录已存在"),
    RECODE_NOT_EXIST("00002","{0}记录不存在"),
    FIELD_IS_NOT_NULL("00003", "{0}字段不为空"),
    OBJECT_IS_NOT_NULL("00004", "{0}对象不为空"),
    CONTROL_ILLEGAL("00005","非法操作"),
    HAS_LEAAF_NOT_DELETE("00006","{0}还有子节点不允许删除,请先删除子节点"),
    STRING_FORMATE_DATE_ERROR("00007","日期格式化错误"),
    CONTROL_INVALID("00008","无效操作"),
    RECODE_STATUS_EXCUTE("00009","记录状态为{0}，不允许操作"),
    DATA_FORMATE_ERROR("00010","{0}数据内容有误"),
    DATA_VAILD_ERROR("00010","{0}时间顺序有误"),
    USER_ALREADY_EXIST("00011","{0}用户已经存在"),
    USER_EMAILS_ALREADY_EXIST("00012","{0}用户邮箱已经存在"),
    
    UPLOAD_FILE_SIZE_ERROR("00013","文件上传过大不能超过3M"),
    UPLOAD_FILE_DIR_ERROR("00014","文件目录不存在"),
    APP_ACCESS_JSON_ERROR("00015","访问方式有误，请检查Accept是否添加【application/json】"),
    APP_USER_LOGIN_ERROR("00016","用户登录帐号或密码有误"),
    
    STATION_CODE_ERROR("00017","站点编码格式错误"),
    STATION_LIST_ERROR("00018","站点列表不少于2个（包含），不多于5个（包含）"),
    
    USER_REGEDIT_MOBILE_ERROR("00019","用户手机号为空，请填写正确的手机号码"),
    USER_LOGIN_MOBILE_TIMEOUT_ERROR("00020","用户登录超时，请重新登录"),
    
    GOBACK_TRUCK_HAS_WAYBILL("00021","该车源已提交运单，不能删除"),
    GOBACK_GOODS_HAS_WAYBILL("00022","该货源已提交运单，不能取消,删除或发布"),
    
    USER_REGEDIT_MOBILE_CODE_ERROR("00023","验证码输入有误，请核对验证短信"),
    
    GOBACK_GOODS_STATUS_HAS_NO_TRANSPORTING("00024","货源状态不为运输中，不能取消或完成运单"),
    WAYBILL_STATUS_HAS_NO_FINISH("00025","运单尚未完成，不能完成货源"),
    GOBACK_GOODS_HAS_NO_PUBLISHING("00026","非发布中的货源，不能提交运单"),
    HAS_DATA_NOT_DELETE("00027","该分类下还有数据不允许删除，请先删除数据"),
    HAS_NOT_DEFAULT_VALUE("00028","没有默认数据"),
    IMAGE_TYPE_ERROR("00029","上传图片格式不支持");
    

    private String code;
    private String content;
    private ExceptionCodes(String code, String content){
        this.code = code;
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
