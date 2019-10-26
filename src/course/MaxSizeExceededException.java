package course;

/**
 * Added by JME
 * created class extending Exception
 * created constructor (int)
 * created getSize()
 */
public class MaxSizeExceededException extends Exception {
    private int maxSize = 10;
    public MaxSizeExceededException(String message) {
        super(message);
    }

    public MaxSizeExceededException(int size) {
        this("Max size defined to :" + size);
        maxSize=size;
    }

    public int getSize(){
        return maxSize;
    }
}
