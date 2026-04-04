export function formatUsd(value: number | string): string {
    const parsedValue = Number(value)

    if (Number.isNaN(parsedValue)) {
        return '--'
    }

    return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }).format(parsedValue)
}
