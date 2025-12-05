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

package org.applied_geodesy.instrument.totalstaion.leica.data;

import org.applied_geodesy.instrument.totalstaion.leica.es.BOOLE;

public class AUT_SEARCH_AREA {
	public double dCenterHz; // Hz angle of search area – center [rad]
	public double dCenterV;	// V angle of search area – center [rad]
	public double dRangeHz;	// width of search area [rad]
	public double dRangeV;	// maximal height of search area [rad]
	public BOOLE bEnabled;	// TRUE: user defined search area is active
	
	public AUT_SEARCH_AREA(double dCenterHz, double dCenterV, double dRangeHz,
			double dRangeV, BOOLE bEnabled) {
		this.dCenterHz = dCenterHz;
		this.dCenterV = dCenterV;
		this.dRangeHz = dRangeHz;
		this.dRangeV = dRangeV;
		this.bEnabled = bEnabled;
	}
	
}
