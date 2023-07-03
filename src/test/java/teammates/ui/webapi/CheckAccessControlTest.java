package teammates.ui.webapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.jmeter.modifiers.UserParameters;
import org.junit.Test;
import org.testng.annotations.BeforeMethod;

import net.sf.saxon.functions.ConstantFunction.False;
import net.sf.saxon.functions.ConstantFunction.True;
import teammates.common.datatransfer.DataBundle;
import teammates.common.datatransfer.InstructorPrivileges;
import teammates.common.datatransfer.UserInfo;
import teammates.common.datatransfer.attributes.CourseAttributes;
import teammates.common.datatransfer.attributes.InstructorAttributes;
import teammates.common.datatransfer.attributes.StudentAttributes;
import teammates.common.util.Config;
import teammates.common.util.Const;
import teammates.common.util.EmailWrapper;
import teammates.common.util.JsonUtils;
import teammates.logic.api.LogicExtension;
import teammates.logic.api.MockAuthProxy;
import teammates.logic.api.MockEmailSender;
import teammates.logic.api.MockLogsProcessor;
import teammates.logic.api.MockRecaptchaVerifier;
import teammates.logic.api.MockTaskQueuer;
import teammates.logic.api.MockUserProvision;
import teammates.test.BaseTestCaseWithLocalDatabaseAccess;
import teammates.test.MockHttpServletRequest;
import teammates.ui.request.BasicRequest;
import teammates.ui.request.InvalidHttpRequestBodyException;


public class CheckAccessControlTest extends Action<CheckAccessControlTest> {
    
    @Test
    protected void testCase1{
        String userParam = 345;
        UserInfo userInfo = null;
        authType = AuthType.PUBLIC
        getMinAuthLevel = AuthType.PUBLIC
        
        assert((checkAccessControl(userParam, userInfo, authType, getMinAuthLevel), "User " + userInfo.id+ " is trying to masquerade as " + userParam + " without admin permission."));
    }

    @Test
    protected void testCase2{
        String userParam = 345;
        UserInfo userInfo;
        userInfo.id = 123;
        userInfo.isAdmin = False;
        authType = AuthType.PUBLIC
        getMinAuthLevel = AuthType.PUBLIC
        
        assert((checkAccessControl(userParam, userInfo, authType, getMinAuthLevel), "User " + userInfo.id+ " is trying to masquerade as " + userParam + " without admin permission."));
    }

    @Test
    protected void testCase3{
        String userParam = 345;
        UserInfo userInfo;
        userInfo.id = 123;
        userInfo.isAdmin = False;
        authType = AuthType.PUBLIC
        getMinAuthLevel = AuthType.PUBLIC
        
        assert((checkAccessControl(userParam, userInfo, authType, getMinAuthLevel), checkSpecificAccessControl()));
    }

    @Test
    protected void testCase4{
        String userParam = 345;
        UserInfo userInfo;
        userInfo.id = 123;
        userInfo.isAdmin = False;
        authType = AuthType.ALL_ACCESS
        getMinAuthLevel = AuthType.PUBLIC
        
        assert((checkAccessControl(userParam, userInfo, authType, getMinAuthLevel), return));
    }

    @Test
    protected void testCase5{
        String userParam = 345;
        UserInfo userInfo;
        userInfo.id = 123;
        userInfo.isAdmin = False;
        authType = AuthType.PUBLIC
        getMinAuthLevel = AuthType.LOGGED_IN
        
        assert((checkAccessControl(userParam, userInfo, authType, getMinAuthLevel), "Not authorized to access this resource."));
    }

     @Test
    protected void testCase6{
        String userParam = null;
        UserInfo userInfo = null;
        authType = AuthType.PUBLIC
        getMinAuthLevel = AuthType.PUBLIC
        
        assert((checkAccessControl(userParam, userInfo, authType, getMinAuthLevel), "User " + userInfo.id+ " is trying to masquerade as " + userParam + " without admin permission."));
    }

     @Test
    protected void testCase7{
        String userParam = 345;
        UserInfo userInfo;
        userInfo.id = 123;
        userInfo.isAdmin = True;
        authType = AuthType.PUBLIC
        getMinAuthLevel = 2
        
        assert((checkAccessControl(userParam, userInfo, authType, getMinAuthLevel), "User " + userInfo.id+ " is trying to masquerade as " + userParam + " without admin permission."));
    }

     @Test
    protected void testCase8{
        String userParam = 123;
        UserInfo userInfo;
        userInfo.id = 123;
        userInfo.isAdmin = False;
        authType = AuthType.PUBLIC
        getMinAuthLevel = 2
        
        assert((checkAccessControl(userParam, userInfo, authType, getMinAuthLevel), "User " + userInfo.id+ " is trying to masquerade as " + userParam + " without admin permission."));
    }
}
