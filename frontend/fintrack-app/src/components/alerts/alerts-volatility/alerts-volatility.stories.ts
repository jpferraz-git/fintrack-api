import type { Meta, StoryObj } from '@storybook/angular';
import { AlertsVolatility } from './alerts-volatility.component';

const meta: Meta<AlertsVolatility> = {
  title: 'Components/AlertsVolatility',
  component: AlertsVolatility,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<AlertsVolatility>;

export const Primary: Story = {
  args: {},
};
