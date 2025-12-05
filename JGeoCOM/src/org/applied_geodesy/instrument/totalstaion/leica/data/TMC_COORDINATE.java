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

public class TMC_COORDINATE {
	public double dE; // E-Coordinate [m]
	public double dN; // N-Coordinate [m]
	public double dH; // H-Coordinate [m]
	public long coordTime; // Timestamp of dist. Measurement [ms]
	public double dE_Cont; // E-Coordinate (continuously) [m]
	public double dN_Cont; // N-Coordinate (continuously) [m]
	public double dH_Cont; // H-Coordinate (continuously) [m]
	public long coordContTime; // Timestamp of measurement [ms]
	
	public TMC_COORDINATE(double dE, double dN, double dH) {
		this(dE,dN,dH,0L,0,0,0,0L);
	}
	
	public TMC_COORDINATE(double dE, double dN, double dH, long coordTime,
			double dE_Cont, double dN_Cont, double dH_Cont, long coordContTime) {
		this.dE = dE;
		this.dN = dN;
		this.dH = dH;
		this.coordTime = coordTime;
		this.dE_Cont = dE_Cont;
		this.dN_Cont = dN_Cont;
		this.dH_Cont = dH_Cont;
		this.coordContTime = coordContTime;
	}
}
