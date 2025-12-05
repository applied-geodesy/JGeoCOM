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

public class TMC_ATMOS_TEMPERATURE {
	public double dLambda; // Wave length of the EDM transmitter [m]
	public double dPressure; // Atmospheric pressure [mbar]
	public double dDryTemperature; // Dry temperature [°C]
	public double dWetTemperature; // Wet temperature [°C]
	public TMC_ATMOS_TEMPERATURE(double dLambda, double dPressure,
			double dDryTemperature, double dWetTemperature) {
		this.dLambda = dLambda;
		this.dPressure = dPressure;
		this.dDryTemperature = dDryTemperature;
		this.dWetTemperature = dWetTemperature;
	}
}
