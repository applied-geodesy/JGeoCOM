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

public enum EDM_EGLINTENSITY_TYPE {
	EDM_EGLINTEN_HIGH 	(3, "Intensity of Electronic Guidelight hight"),
	EDM_EGLINTEN_MID 	(2, "Intensity of Electronic Guidelight middle"),
	EDM_EGLINTEN_LOW 	(1, "Intensity of Electronic Guidelight low"),
	EDM_EGLINTEN_OFF 	(0, "Electronic Guidelight off");

	private final int value;
	private String description;
	
	private EDM_EGLINTENSITY_TYPE(int value, String description) {
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

	public static EDM_EGLINTENSITY_TYPE getEnumByValue(int value) {
		for(EDM_EGLINTENSITY_TYPE element : EDM_EGLINTENSITY_TYPE.values()) {
			if(element.getValue() == value)
				return element;
		}
		return null;
	}  
}
