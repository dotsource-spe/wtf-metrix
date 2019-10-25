package de.dotsource.wtf.client.api;

import de.dotsource.wtf.client.invoker.ApiClient;

import de.dotsource.wtf.client.model.Metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-10-25T21:44:05.231Z")
@Component("de.dotsource.wtf.client.api.MetricsApi")
public class MetricsApi {
    private ApiClient apiClient;

    public MetricsApi() {
        this(new ApiClient());
    }

    @Autowired
    public MetricsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Finds metric
     * Muliple tags can be provided with comma separated strings. Use         tag1, tag2, tag3 for testing.
     * <p><b>200</b> - successful operation
     * <p><b>204</b> - No Feedback was submitted for that file.
     * <p><b>400</b> - Invalid tag value
     * @param repo Repo to filter by
     * @param path Path to filter by
     * @param revision Revision to filter by
     * @return List&lt;Metrics&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<Metrics> findPetsByTags(String repo, String filePath, String revision) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'repo' is set
        if (repo == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'repo' when calling findPetsByTags");
        }
        
        // verify the required parameter 'path' is set
        if (filePath == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'path' when calling findPetsByTags");
        }
        
        // verify the required parameter 'revision' is set
        if (revision == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'revision' when calling findPetsByTags");
        }
        
        String path = UriComponentsBuilder.fromPath("/metric").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "repo", repo));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "path", filePath));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "revision", revision));

        final String[] accepts = { 
            "application/xml", "application/json"
        };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<Metrics>> returnType = new ParameterizedTypeReference<List<Metrics>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
