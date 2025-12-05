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

import org.applied_geodesy.instrument.totalstaion.leica.es.TMC_FACE;

public class TMC_ANGLE {
	public double dHz; // Horizontal angle [rad]
	public double dV; // Vertical angle [rad]
	public double dAngleAccuracy; // Accuracy of angles [rad]
	public long AngleTime; // Moment of measurement [ms]
	public TMC_INCLINE Incline; // Corresponding inclination
	public TMC_FACE eFace; // Face position of telescope
	public TMC_ANGLE(double dHz, double dV, double dAngleAccuracy,
			long angleTime, TMC_INCLINE incline, TMC_FACE eFace) {
		this.dHz = dHz;
		this.dV = dV;
		this.dAngleAccuracy = dAngleAccuracy;
		AngleTime = angleTime;
		Incline = incline;
		this.eFace = eFace;
	}
	
}
