/**
 *
 * @author Pooja Purohit
 */

package com.carematcher.search;


public enum SearchType {
    
    /** Search for any user
     * 
     */
    USER,
    
    /** Search specifically for providers
     * 
     */
    PROVIDER,
    
    /** Search specifically for practices
     * 
     */
    PRACTICE,
    
    /** For searching for services by either name or description
     * 
     */
    SERVICE,
    
    /** For searching for services by the name field only (service lookup)
     * 
     */
    SERVICE_BY_NAME,
    
    /** For searching specifically for insurances
     * 
     */
    INSURANCE,
    
    /** For searching specifically for licenses 
     * 
     */
    LICENSE
}
