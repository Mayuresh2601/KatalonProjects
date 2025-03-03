package internal

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.main.TestCaseMain


/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */
public class GlobalVariable {
     
    /**
     * <p></p>
     */
    public static Object keycloak
     
    /**
     * <p></p>
     */
    public static Object authBasePath
     
    /**
     * <p></p>
     */
    public static Object baseURL_QA
     
    /**
     * <p></p>
     */
    public static Object baseURL_QA_old
     
    /**
     * <p></p>
     */
    public static Object basePathAPI
     
    /**
     * <p></p>
     */
    public static Object baseURL_QA_MIG
     
    /**
     * <p></p>
     */
    public static Object basePathNFE
     
    /**
     * <p></p>
     */
    public static Object grant_type
     
    /**
     * <p></p>
     */
    public static Object client_id
     
    /**
     * <p></p>
     */
    public static Object client_secret
     
    /**
     * <p></p>
     */
    public static Object basePath
     
    /**
     * <p></p>
     */
    public static Object downloadPath
     
    /**
     * <p></p>
     */
    public static Object keycloakQA
     
    /**
     * <p></p>
     */
    public static Object keycloakQAMIG
     
    /**
     * <p></p>
     */
    public static Object iataTokenQA
     
    /**
     * <p></p>
     */
    public static Object iataTokenQAMIG
     
    /**
     * <p></p>
     */
    public static Object airlineTokenQA
     
    /**
     * <p></p>
     */
    public static Object airlineTokenQAMIG
     
    /**
     * <p></p>
     */
    public static Object author
     
    /**
     * <p></p>
     */
    public static Object extentFlag
     
    /**
     * <p></p>
     */
    public static Object airline952_73783_QA
     
    /**
     * <p></p>
     */
    public static Object airline952_73783_QAMIG
     
    /**
     * <p></p>
     */
    public static Object airlineQA73782113
     
    /**
     * <p></p>
     */
    public static Object airlineQAMIG73782113
     
    /**
     * <p></p>
     */
    public static Object iataQA_CH
     
    /**
     * <p></p>
     */
    public static Object iataQAMIG_CH
     
    /**
     * <p></p>
     */
    public static Object airline250QA
     
    /**
     * <p></p>
     */
    public static Object airline250QAMIG
     
    /**
     * <p></p>
     */
    public static Object airline695QA
     
    /**
     * <p></p>
     */
    public static Object airline695QAMIG
     
    /**
     * <p></p>
     */
    public static Object dpcIN_QA
     
    /**
     * <p></p>
     */
    public static Object dpcIN_QAMIG
     
    /**
     * <p></p>
     */
    public static Object airline450QA
     
    /**
     * <p></p>
     */
    public static Object airline450QAMIG
     
    /**
     * <p></p>
     */
    public static Object agent73786127QA
     
    /**
     * <p></p>
     */
    public static Object agent73786127QAMIG
     
    /**
     * <p></p>
     */
    public static Object airlineQA_001_67723190
     
    /**
     * <p></p>
     */
    public static Object airlineQAMIG_001_67723190
     
    /**
     * <p></p>
     */
    public static Object airlineQA_026_6772279
     
    /**
     * <p></p>
     */
    public static Object airlineQAMIG_026_6772279
     
    /**
     * <p></p>
     */
    public static Object airlineQA_157_686910
     
    /**
     * <p></p>
     */
    public static Object airlineQAMIG_157_686910
     
    /**
     * <p></p>
     */
    public static Object agent0230010QA
     
    /**
     * <p></p>
     */
    public static Object agent0230010QAMIG
     
    /**
     * <p></p>
     */
    public static Object airlineQA_191_67724465
     
    /**
     * <p></p>
     */
    public static Object airlineQAMIG_191_67724465
     
    /**
     * <p></p>
     */
    public static Object baseURL
     

    static {
        try {
            def selectedVariables = TestCaseMain.getGlobalVariables("default")
			selectedVariables += TestCaseMain.getGlobalVariables(RunConfiguration.getExecutionProfile())
            selectedVariables += TestCaseMain.getParsedValues(RunConfiguration.getOverridingParameters(), selectedVariables)
    
            keycloak = selectedVariables['keycloak']
            authBasePath = selectedVariables['authBasePath']
            baseURL_QA = selectedVariables['baseURL_QA']
            baseURL_QA_old = selectedVariables['baseURL_QA_old']
            basePathAPI = selectedVariables['basePathAPI']
            baseURL_QA_MIG = selectedVariables['baseURL_QA_MIG']
            basePathNFE = selectedVariables['basePathNFE']
            grant_type = selectedVariables['grant_type']
            client_id = selectedVariables['client_id']
            client_secret = selectedVariables['client_secret']
            basePath = selectedVariables['basePath']
            downloadPath = selectedVariables['downloadPath']
            keycloakQA = selectedVariables['keycloakQA']
            keycloakQAMIG = selectedVariables['keycloakQAMIG']
            iataTokenQA = selectedVariables['iataTokenQA']
            iataTokenQAMIG = selectedVariables['iataTokenQAMIG']
            airlineTokenQA = selectedVariables['airlineTokenQA']
            airlineTokenQAMIG = selectedVariables['airlineTokenQAMIG']
            author = selectedVariables['author']
            extentFlag = selectedVariables['extentFlag']
            airline952_73783_QA = selectedVariables['airline952_73783_QA']
            airline952_73783_QAMIG = selectedVariables['airline952_73783_QAMIG']
            airlineQA73782113 = selectedVariables['airlineQA73782113']
            airlineQAMIG73782113 = selectedVariables['airlineQAMIG73782113']
            iataQA_CH = selectedVariables['iataQA_CH']
            iataQAMIG_CH = selectedVariables['iataQAMIG_CH']
            airline250QA = selectedVariables['airline250QA']
            airline250QAMIG = selectedVariables['airline250QAMIG']
            airline695QA = selectedVariables['airline695QA']
            airline695QAMIG = selectedVariables['airline695QAMIG']
            dpcIN_QA = selectedVariables['dpcIN_QA']
            dpcIN_QAMIG = selectedVariables['dpcIN_QAMIG']
            airline450QA = selectedVariables['airline450QA']
            airline450QAMIG = selectedVariables['airline450QAMIG']
            agent73786127QA = selectedVariables['agent73786127QA']
            agent73786127QAMIG = selectedVariables['agent73786127QAMIG']
            airlineQA_001_67723190 = selectedVariables['airlineQA_001_67723190']
            airlineQAMIG_001_67723190 = selectedVariables['airlineQAMIG_001_67723190']
            airlineQA_026_6772279 = selectedVariables['airlineQA_026_6772279']
            airlineQAMIG_026_6772279 = selectedVariables['airlineQAMIG_026_6772279']
            airlineQA_157_686910 = selectedVariables['airlineQA_157_686910']
            airlineQAMIG_157_686910 = selectedVariables['airlineQAMIG_157_686910']
            agent0230010QA = selectedVariables['agent0230010QA']
            agent0230010QAMIG = selectedVariables['agent0230010QAMIG']
            airlineQA_191_67724465 = selectedVariables['airlineQA_191_67724465']
            airlineQAMIG_191_67724465 = selectedVariables['airlineQAMIG_191_67724465']
            baseURL = selectedVariables['baseURL']
            
        } catch (Exception e) {
            TestCaseMain.logGlobalVariableError(e)
        }
    }
}
