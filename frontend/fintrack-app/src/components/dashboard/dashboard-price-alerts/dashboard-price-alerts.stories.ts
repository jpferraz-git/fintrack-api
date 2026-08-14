import type { Meta, StoryObj } from '@storybook/angular';
import { DashboardPriceAlerts } from './dashboard-price-alerts.component';

const meta: Meta<DashboardPriceAlerts> = {
  title: 'Components/DashboardPriceAlerts',
  component: DashboardPriceAlerts,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<DashboardPriceAlerts>;

export const Primary: Story = {
  args: {},
};
