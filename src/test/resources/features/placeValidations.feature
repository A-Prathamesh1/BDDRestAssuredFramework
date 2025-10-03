Feature: Validating place API's

  @AddPlace @Regression
  Scenario Outline: Verify if place is being added using AddPlaceAPI
    Given Add place Payload with "<name>" "<language>" "<address>"
    When user calls "addPlaceAPI" with "post" HTTP request
    Then API call is success with status 200
    And "status" in response is "OK"
    And "scope" in response body is "APP"
    And verify the place_id created, maps to the "<name>" using "getPlaceAPI"

    Examples:
      | name       | language | address             |
      | Prathamesh | Marathi  | B-602 Dayal Heights |
    #|Pratham1|Maithili1|B-901 Royal Rahadki1|

  @DeletePlace @Regression
  Scenario Outline: Verify if delete place functionality is working
    Given user has payload with place_id of place to be deleted
    When user calls "deletePlaceAPI" with "delete" HTTP request
    Then API call is success with status 200
    #And "status" in response is "OK"
    #And verify the "<msg>" using "getPlaceAPI" of "place_id" of deleted place,

    Examples:
      | msg                                                       |
      | Get operation failed, looks like place_id  doesn't exists |