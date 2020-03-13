# service_request_jira_api

1. Get service request subscription in JIRA
2. Create API key for Auth
3. Use below API to create Service Request
4. https://developer.atlassian.com/cloud/jira/platform/rest/v2/

5. GET request to know about JIRA IS ETC

curl --location --request GET 'https://kaleeswaran.atlassian.net/rest/servicedeskapi/request' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic DUMMY==' \

6. Create Issue

curl --location --request POST 'https://kaleeswaran.atlassian.net/rest/servicedeskapi/request' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic DUMMY' \
--data-raw '{
    "serviceDeskId": "2",
    "requestTypeId": "10004",
    "requestFieldValues": {
        "summary": "Request JSD help via REST",
        "description": "I need a new mouse for my Mac"
    }
}'



