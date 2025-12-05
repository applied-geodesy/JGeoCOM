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

public enum BAP_ATRSETTING {
	/**
	 * UNVALIDIERT  --- Keine Nummern im Manual!!!
	 */
	BAP_ATRSET_NORMAL     (0, "ATR is using no special flags or modes"),
	@Deprecated
	BAP_ATRSET_LOWVIS_ON  (1,"ATR low vis mode on"),
	@Deprecated
	BAP_ATRSET_LOWVIS_AON (2,"ATR low vis mode always on"),
	@Deprecated
	BAP_ATRSET_SRANGE_ON  (3, "ATR high reflectivity mode on"),
	@Deprecated
	BAP_ATRSET_SRANGE_AON (4, "ATR high reflectivity mode always on");
	
	private final int value;
	private String description;
	
	private BAP_ATRSETTING(int value, String description) {
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

	public static BAP_ATRSETTING getEnumByValue(int value) {
		for(BAP_ATRSETTING element : BAP_ATRSETTING.values()) {
			if(element.getValue() == value)
				return element;
		}
		return null;
	}  
}
