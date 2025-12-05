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

public enum BAP_USER_MEASPRG {
	BAP_SINGLE_REF_PRECISE  	(11, "IR Precise (TS30,TM30)"),
	BAP_CONT_REF_SYNCHRO    	(10, "IR Synchro Tracking"),
	BAP_AVG_RLESS_VISIBLE   	(9, "Average RL distance, reflector free (red laser)"),
	BAP_AVG_REF_VISIBLE     	(8, "Average long range dist. with reflector (red)"),
	BAP_AVG_REF_STANDARD    	(7, "Average IR distance with reflector"),
	BAP_CONT_RLESS_VISIBLE   	(6, "fast tracking RL distance, reflector free (red)"),
	BAP_CONT_REF_FAST       	(5, "not supported by TPS1200"),
	@Deprecated
	BAP_CONT_REF_STANDARD   	(4, "tracking IR distance with reflector"),
	BAP_SINGLE_RLESS_VISIBLE  	(3, "single RL distance, reflector free (red laser)"),
	BAP_SINGLE_REF_VISIBLE  	(2, "long range distance with reflector (red laser)"),
	BAP_SINGLE_REF_FAST     	(1, "fast single IR distance with reflector"),
	BAP_SINGLE_REF_STANDARD  	(0, "standard single IR distance with reflector");

	private final int value;
	private String description;
	
	private BAP_USER_MEASPRG(int value, String description) {
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

	public static BAP_USER_MEASPRG getEnumByValue(int value) {
		for(BAP_USER_MEASPRG element : BAP_USER_MEASPRG.values()) {
			if(element.getValue() == value)
				return element;
		}
		return null;
	}  
}

