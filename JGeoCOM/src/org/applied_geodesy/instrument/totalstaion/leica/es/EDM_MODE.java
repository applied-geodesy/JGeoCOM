/***********************************************************************
* Copyright by Michael Loesler, https://software.applied-geodesy.org   *
*                                                                      *
* This program is free software; you can redistribute it and/or modify *
* it under the terms of the GNU General Public License as published by *
* the Free Software Foundation; either version 3 of the License, or    *
* at your option any later version.                                    *
*                                                                      *
* This program is distributed in the hope that it will be useful,      *
* but WITHOUT ANY WARRANTY; without even the implied warranty of       *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the        *
* GNU General Public License for more details.                         *
*                                                                      *
* You should have received a copy of the GNU General Public License    *
* along with this program; if not, see <http://www.gnu.org/licenses/>  *
* or write to the                                                      *
* Free Software Foundation, Inc.,                                      *
* 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.            *
*                                                                      *
***********************************************************************/

package org.applied_geodesy.instrument.totalstaion.leica.es;

public enum EDM_MODE {
	EDM_PRECISE_TAPE     	(14, "IR Precise Reflector Tape (TS30, TM30)"),
	EDM_PRECISE_IR      	(13, "IR Precise (TS30, TM30)"),
	EDM_AVERAGE_LR      	(12, "Long range average measurement"),
	EDM_AVERAGE_SR       	(11, "Short range average measurement"),
	EDM_AVERAGE_IR      	(10, "Standard average measurement"),
	EDM_CONT_FAST        	(9, "Fast repeated measurement"),
	EDM_CONT_REFLESS    	(8, "Reflectorless repeated measurement"),
	EDM_CONT_DYNAMIC    	(7, "Dynamic repeated measurement"),
	EDM_CONT_STANDARD   	(6, "Standard repeated measurement"),
	EDM_SINGLE_SRANGE   	(5, "Short range single measurement"),
	EDM_SINGLE_LRANGE    	(4, "Long range single measurement"),
	EDM_SINGLE_FAST     	(3, "Fast single measurement"),
	EDM_SINGLE_STANDARD 	(2, "Standard single measurement"),
	EDM_SINGLE_TAPE     	(1, "Single measurement with tape"),
	EDM_MODE_NOT_USED    	(0, "Init value");
	
	private final int value;
	private String description;
	
	private EDM_MODE(int value, String description) {
		this.value = value;
		this.description = description;
	}
	
	public int getValue() {
		return value;
	}
	
	public String toString() {
		return this.name() + "(" + this.value + "): " + this.description;
	}
	
	public String getDescription() {
		return this.description;
	}

	public static EDM_MODE getEnumByValue(int value) {
		for(EDM_MODE element : EDM_MODE.values()) {
			if(element.getValue() == value)
				return element;
		}
		return null;
	}  
}
