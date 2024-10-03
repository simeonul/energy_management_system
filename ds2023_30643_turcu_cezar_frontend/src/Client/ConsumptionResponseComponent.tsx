import React from 'react';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogActions from '@mui/material/DialogActions';
import Button from '@mui/material/Button';
import {ConsumptionResponse} from '../ConsumptionResponse/ConsumptionResponse';
import {BarPlot, LinePlot, ChartContainer, AllSeriesType, ChartsXAxis, ChartsYAxis} from '@mui/x-charts';
import './ConsumptionResponseComponent.css';

interface ResponseDialogProps {
    isOpen: boolean;
    onClose: () => void;
    responseArray: ConsumptionResponse[];
}

const ConsumptionResponseComponent: React.FC<ResponseDialogProps> = ({isOpen, onClose, responseArray}) => {
    const isNoDataAvailable = responseArray.every(response => response.consumption === 0);
    const hourArray = responseArray.map(response => response.hour);
    const consumptionArray = responseArray.map(response => response.consumption);
    const series = [
        {
            type: 'bar',
            stack: '',
            yAxisKey: 'consumption',
            color: '#3489eb',
            data: consumptionArray,
        },
        {
            type: 'line',
            yAxisKey: 'consumption',
            color: 'red',
            data: consumptionArray,
        },
    ] as AllSeriesType[];

    const dialogPaperStyle = isNoDataAvailable
        ? {}
        : {
            width: '650px',
            maxWidth: 'none',
            height: '550px',
            maxHeight: 'none',
        };


    return (
        <Dialog open={isOpen} onClose={onClose} className="my-dialog" PaperProps={{ style: dialogPaperStyle }}>
            <DialogTitle>Total hourly energy consumption</DialogTitle>
            <DialogContent>
                {isNoDataAvailable ? (
                    <DialogContentText>There is no available data for the selected date!</DialogContentText>
                ) : (
                    <ChartContainer
                        className="container-content"
                        series={series}
                        width={600}
                        height={400}
                        xAxis={[
                            {
                                id: 'hour',
                                data: hourArray,
                                scaleType: 'band',
                                valueFormatter: (value) => value.toString(),
                            },
                        ]}
                        yAxis={[
                            {
                                id: 'consumption',
                                scaleType: 'linear',
                            }
                        ]}
                    >
                        <BarPlot/>
                        <LinePlot/>
                        <ChartsXAxis
                            label="Hour"
                            position="bottom"
                            axisId="hour"
                        />
                        <ChartsYAxis
                            label="Consumption"
                            position="left"
                            axisId="consumption"/>
                    </ChartContainer>
                )}
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Close</Button>
            </DialogActions>
        </Dialog>
    );
};

export default ConsumptionResponseComponent;