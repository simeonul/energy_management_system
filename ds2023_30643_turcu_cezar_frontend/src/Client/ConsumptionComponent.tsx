import React, {useState} from 'react';
import dayjs, {Dayjs} from 'dayjs';
import utc from 'dayjs/plugin/utc';
import timezone from 'dayjs/plugin/timezone';
import {DemoContainer} from '@mui/x-date-pickers/internals/demo';
import {LocalizationProvider} from '@mui/x-date-pickers/LocalizationProvider';
import {AdapterDayjs} from '@mui/x-date-pickers/AdapterDayjs';
import {StaticDatePicker} from '@mui/x-date-pickers/StaticDatePicker';
import ConsumptionResponseComponent from './ConsumptionResponseComponent';
import {ConsumptionResponse} from '../ConsumptionResponse/ConsumptionResponse';

dayjs.extend(utc);
dayjs.extend(timezone);
const ConsumptionComponent: React.FC = () => {
    const currentDay = dayjs();
    const [value, setValue] = React.useState<Dayjs | null>(currentDay);
    const [responseArray, setResponseArray] = useState<ConsumptionResponse[]>([]);
    const [isDialogOpen, setDialogOpen] = useState<boolean>(false);
    dayjs.tz.setDefault('Europe/Bucharest');

    const processDatePicked = (newValue: dayjs.Dayjs | null) => {
        const unixEpochTime = newValue ? newValue.tz('Europe/Bucharest').unix() : null;
        const userId = sessionStorage.getItem('userId');
        const jwtToken = sessionStorage.getItem('jwt');
        if (userId && jwtToken) {
            const requestBody = {
                userId: userId,
                timestamp: unixEpochTime,
            };

            fetch('http://localhost:8082/device-consumption', {
                method: 'POST',
                headers: {
                    'Authorization': jwtToken,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestBody),
            })
                .then(response => response.json())
                .then((data: ConsumptionResponse[]) => {
                    setResponseArray(data);
                    setDialogOpen(true);
                })
                .catch();
        }
    }

    const handleDialogClose = () => {
        setDialogOpen(false);
    };


    return (
        <>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DemoContainer components={['DatePicker']}>
                    <StaticDatePicker
                        value={value}
                        onChange={(newValue) => setValue(newValue)}
                        onAccept={(newValue) => processDatePicked(newValue)}
                        maxDate={currentDay}
                        orientation={"landscape"}
                    />
                </DemoContainer>
            </LocalizationProvider>
            <ConsumptionResponseComponent
                isOpen={isDialogOpen}
                onClose={handleDialogClose}
                responseArray={responseArray} />
        </>
    );
}

export default ConsumptionComponent;