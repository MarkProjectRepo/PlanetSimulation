/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package drawing;

/**
 *
 * @author matra4214
 */
public class Tuple<S, T> {
    public Tuple(S s, T t) {
        this.first = s;
        this.second = t;
    }
    public Tuple(){
        
    }
    
    public S first;
    public T second;
}
