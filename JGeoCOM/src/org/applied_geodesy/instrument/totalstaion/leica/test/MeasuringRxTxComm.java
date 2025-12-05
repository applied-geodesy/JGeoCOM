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

package org.applied_geodesy.instrument.totalstaion.leica.test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.applied_geodesy.instrument.totalstaion.leica.GCDataPacket;
import org.applied_geodesy.instrument.totalstaion.leica.JGeoCOM;
import org.applied_geodesy.instrument.totalstaion.leica.data.TMC_COORDINATE;
import org.applied_geodesy.instrument.totalstaion.leica.data.TMC_FULL_MEAS;
import org.applied_geodesy.instrument.totalstaion.leica.es.BAP_USER_MEASPRG;
import org.applied_geodesy.instrument.totalstaion.leica.es.ON_OFF_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.es.TMC_INCLINE_PRG;
import org.applied_geodesy.instrument.totalstaion.leica.es.TMC_MEASURE_PRG;
import org.applied_geodesy.io.rxtx.rxtxcomm.RxTxCommunicator;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class MeasuringRxTxComm implements PropertyChangeListener {

	public static void main(String[] args) throws Exception {
		// For ChangeListener
		MeasuringRxTxComm changeListener = new MeasuringRxTxComm();
		
		RxTxCommunicator comm = new RxTxCommunicator();
		comm.setCommPortIdentifier(CommPortIdentifier.getPortIdentifier("COM11"));
		comm.setBaudRate(9600);
		comm.setDataBits(SerialPort.DATABITS_8);
		comm.setStopBits(SerialPort.STOPBITS_1);
		comm.setParity(SerialPort.PARITY_NONE);
		
		JGeoCOM geoCOM = new JGeoCOM(comm);
		geoCOM.addPropertyChangeListener(changeListener);
		
		comm.addReceiver(geoCOM);

		if (comm.open()) {
			try {
				GCDataPacket packet = null;
				
				packet = geoCOM.AUS_SetUserAtrState(ON_OFF_TYPE.ON);
				System.out.println(packet.getGRC()+"   "+packet.getOnAnswerArgument());
				
				packet = geoCOM.BAP_SetMeasPrg(BAP_USER_MEASPRG.BAP_SINGLE_REF_STANDARD);
				System.out.println(packet.getGRC()+"   "+packet.getOnAnswerArgument());
				
				packet = geoCOM.TMC_DoMeasure(TMC_MEASURE_PRG.TMC_DEF_DIST, TMC_INCLINE_PRG.TMC_AUTO_INC);
				System.out.println(packet.getGRC()+"   "+packet.getOnAnswerArgument());
				
				packet = geoCOM.TMC_GetFullMeas(12500L, TMC_INCLINE_PRG.TMC_AUTO_INC);
				TMC_FULL_MEAS meas = (TMC_FULL_MEAS)packet.getOnAnswerArgument();
				System.out.println(meas.rdHzAngle+"  "+meas.rdVAngle+"   "+meas.rdSlopeDist+"  "+meas.rdAccuracyAngle+"  "+meas.rdDistTime);
				
				packet = geoCOM.TMC_GetCoordinate(12500L, TMC_INCLINE_PRG.TMC_AUTO_INC);
				TMC_COORDINATE coords = (TMC_COORDINATE)packet.getOnAnswerArgument();
				System.out.println(System.currentTimeMillis()+"  "+packet.getGRC()+"   "+coords.dE+"   "+coords.dN+"   "+coords.dH+"    "+coords.coordTime);
				
			}
			finally {
				comm.close();
				comm.removeReceiver(geoCOM);
				geoCOM.removePropertyChangeListener(changeListener);
			}
		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println(evt.getPropertyName()+"   "+evt.getOldValue()+"  "+evt.getNewValue());
	}
}