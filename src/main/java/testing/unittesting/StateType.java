package testing.unittesting;

import enumerators.Result;

public class StateType {
    private final Result result;
    private final String message;

    /**
     * Constructor creating StateType object
     * @param result
     * @param message
     */
    public StateType(Result result, String message) {
        this.result = result;
        this.message = message;
    }

    /**
     * Returns whether a given unit, area, etc. handling method
     * was successful
     * @return Result enum representing result of a method
     */
    public Result getResult() {
        return this.result;
    }

    /**
     * Returns a given unit, area, etc. handling method's
     * message (i.e. "completed successfully," "validate
     * implementations," etc).
     * @return Result enum representing result of a method
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Constructs and returns a StateType representing a
     * successful method
     * @return StateType success enum
     */
    public static StateType returnSuccess(String message) {
        return new StateType(Result.SUCCESS, message);
    }

    /**
     * Constructs and returns a StateType representing a
     * failed method
     * @return StateType failure enum
     */
    public static StateType returnFailure(String message) {
        return new StateType(Result.FAILURE, message);
    }

    /**
     * Constructs and returns a StateType representing a
     * method that causes a unit to have a number 0 or
     * less (due to battle)
     * @return StateType failure enum
     */
    public static StateType returnDestroy(String message) {
       return new StateType(Result.DESTROY, message);
    }

    /**
     * Checks whether StateType is SUCCESS,
     * allows conditional statements to be more readable
     * @param result of testing method
     * @return boolean
     */
    public static boolean checkIfSuccess(StateType result) {
        return result.getResult() == Result.SUCCESS;
    }

    /**
     * Checks whether StateType is FAILURE,
     * allows conditional statements to be more readable
     * @param result of testing method
     * @return boolean
     */
    public static boolean checkIfFailure(StateType result) {
        return result.getResult() == Result.FAILURE;
    }

    /**
     * Checks whether StateType is DESTROY,
     * allows conditional statements to be more readable
     * @param result of testing method
     * @return boolean
     */
    public static boolean checkIfDestroy(StateType result) {
        return result.getResult() == Result.DESTROY;
    }
}
