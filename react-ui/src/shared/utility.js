export const updateObject = (oldObject, updatedProperties) => {
    return {
        ...oldObject,
        ...updatedProperties
    }
}

export const toHHMMSSinJSON = time => {
    const sec_num = parseInt(time, 10);
    const hours = Math.floor(sec_num / 3600)
    const minutes = Math.floor((sec_num - hours * 3600) / 60);
    const seconds = sec_num - (minutes * 60) - (hours * 3600);
    return {
        hours: hours,
        minutes: minutes,
        seconds: seconds
    }
}

export const toHHMMSSinString = time => {
    return `${time.hours ? +time.hours + ':' : ''}${time.minutes < 10 ? '0' : ''}${time.minutes}:${time.seconds < 10 ? '0' : ''}${time.seconds}`
}
