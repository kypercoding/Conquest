package data.enumerators;

/**
 * States indicating method status.
 * SUCCESS - method is completed successfully
 * FAILURE - method encounters a runtime error
 * DESTROY - unit stats become invalid due to battle
 */
public enum Result {
    SUCCESS, FAILURE, DESTROY
}
